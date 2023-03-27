using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using WebApplication2.Models;
using MySql.Data.MySqlClient;
using System.Windows.Forms;
using Mysqlx.Crud;
using Microsoft.VisualBasic;


namespace WebApplication2.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;   

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Exit()
        {
            return View();
        }

        public IActionResult Unblock()
        {
            int id = int.Parse(Request.Form["unblock"]);
            string connectionString = "SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177";
            MySqlConnection connection = new MySqlConnection(connectionString);
            connection.Open();
            string query = "UPDATE `web_app`.`users` SET `status` = 'not blocked' WHERE (id=" + id + ")";
            MySqlCommand command = new MySqlCommand(query, connection);
            command.ExecuteNonQuery();
            connection.Close();

            return View();
        }


        [HttpPost]
        public IActionResult Block()
        {
            int id = int.Parse(Request.Form["block"]);
            string connectionString = "SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177";
            MySqlConnection connection = new MySqlConnection(connectionString);
            connection.Open();
            string query = "UPDATE `web_app`.`users` SET `status` = 'blocked' WHERE (id="+ id + ")";
            MySqlCommand command = new MySqlCommand(query, connection);
            command.ExecuteNonQuery();
            connection.Close();

            return View();
        }

        public IActionResult Delete()
        {
            int id = int.Parse(Request.Form["delete"]);
            string connectionString = "SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177";
            MySqlConnection connection = new MySqlConnection(connectionString);
            connection.Open();
            string query = "delete from users where(`id` =" + id + ")";
            MySqlCommand command = new MySqlCommand(query, connection);
            command.ExecuteNonQuery();
            connection.Close();

            return View();
        }

        public IActionResult Entry()
        {

            string u_name = Request.Form["email"];
            string pwd = Request.Form["psw"];





            MySqlConnection connection = new MySqlConnection("SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177");
            connection.Open();

            MySqlCommand command = new MySqlCommand();
            command.Connection = connection;
            command.CommandText = "SELECT * FROM users";

            MySqlDataReader reader = command.ExecuteReader();

            List<User> users = new List<User>();
          

            while (reader.Read())
            {
                string id = reader.GetString(0);
                string n = reader.GetString(1);
                string r_date = reader.GetString(2);
                string l_seen = reader.GetString(3);
                string s = reader.GetString(4);
                string p = reader.GetString(5);
             
                users.Add(new User { user_id = int.Parse(id), user_name = n, registration_date = r_date, last_seen= l_seen, status = s, password = p});
            }

            reader.Close();
            connection.Close();


            string connectionString = "SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177";
            MySqlConnection connection1 = new MySqlConnection(connectionString);
            connection1.Open();


            string query1 = "update users set last_seen =" + "'" + DateTime.Now.ToString() + "'" + " where name =" + "'" + u_name + "'";
            MySqlCommand command1 = new MySqlCommand(query1, connection1);
            command1.ExecuteNonQuery();
            connection1.Close();

            for (int i = 0; i < users.Count; ++i)
            {
                if(users.ElementAt(i).user_name.Equals(u_name) && users.ElementAt(i).status.Equals("blocked"))
                {
                    users.ElementAt(0).user_id = -1;
                    return View(users);

                }

                if(users.ElementAt(i).user_name.Equals(u_name) && users.ElementAt(i).password.Equals(pwd))
                {
                    return View(users);
                }
            }
            if (users.Count > 0)
            {
                users.ElementAt(0).user_id = 0;
            }

            return View(users);
        }





        public IActionResult Register()
        {

            MySqlConnection connection = new MySqlConnection("SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177");
            connection.Open();

            MySqlCommand command = new MySqlCommand();
            command.Connection = connection;
            command.CommandText = "SELECT * FROM users";

            MySqlDataReader reader = command.ExecuteReader();
            List<User> l = new List<User>();

            while (reader.Read())
            {
                int u_id = reader.GetInt32(0);
                string u_name = reader.GetString(1);
                string r_date = reader.GetString(2);
                string l_seen = reader.GetString(3);
                string s = reader.GetString(4);
                string p = reader.GetString(5);

                l.Add(new User { user_id = u_id, user_name = u_name, registration_date = r_date, last_seen = l_seen, status = s, password = p});
            }
            reader.Close();

            User user = new User()
            {
                user_id = 0,
            };

            int id = l.Count == 0 ? 1: l.ElementAt(l.Count - 1).user_id + 1;
            string name = Request.Form["email"];
            string registr_date = DateTime.Now.ToString(); 
            string last_seen = DateTime.Now.ToString(); 
            string status = "not blocked";
            string pwd = Request.Form["psw"];
            string r_pwd = Request.Form["r_psw"];
        
             for(int i = 0; i < l.Count; ++i)
             {
                if(l.ElementAt(i).user_name.Equals(name))
                {
                    return View(user);
                }

            }

             if(!pwd.Equals(r_pwd))
            {
                user.user_id = -1;
                return View(user);
            }
      
            

            MySqlCommand cmd = new MySqlCommand("insert into web_app.users (id, name, registr_date, last_seen, status, password) " +
                "values ('"+ id + "','" + name + "','" + registr_date + "','" + last_seen + "','" + status + "','" + pwd + "')", connection);
            cmd.ExecuteNonQuery();


            user = new User()
            {
                user_id = id,
                user_name = name,
                registration_date = registr_date,
                last_seen = last_seen,
                status = status,
                password = pwd
                
            };
            return View(user);
        }
        public IActionResult Index()
        {
            return View();
        }

        public static void openConnection()
        {
            string connection = "SERVER=localhost;DATABASE=web_app;UID=root;PASSWORD=221177";
            MySqlConnection con = new MySqlConnection(connection);
            con.Open();
            string sql = "INSERT INTO `web_app`.`users` (`id`, `name`, `registr_date`, `last_seen`, `status`) VALUES ('4', 'olfgfaaa', '22-03-2023', '22-03-2023', 'exist')";
            MySqlCommand cmd = new(sql, con);
      /*      MySqlDataReader reader = cmd.ExecuteReader();
            while(reader.Read())
            {
                Console.WriteLine(reader["name"]);
            }*/

        }

        public IActionResult SignUp()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}