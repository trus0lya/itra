using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using WebApplication1.Models;
using MySql.Data.MySqlClient;
using System.Runtime.InteropServices;
using Mysqlx.Crud;

namespace WebApplication1.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult AllMessage()
        {
            string interlocutorr = Request.Form["user"];
            List<Message> messages = new List<Message>();
            MySqlConnection connection = new MySqlConnection("SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177");
            connection.Open();
            string query = "select * from messenger where name = '" + nname + "' and interlocutor ='" +  interlocutorr + "' or " +
                "name ='"+ interlocutorr +  "' and interlocutor = '" +  nname + "'";
            MySqlCommand cmd = new MySqlCommand(query, connection);

            MySqlDataReader reader = cmd.ExecuteReader();
            

            while (reader.Read())
            {
            

                string s = reader.GetString(1);
                string r = reader.GetString(2);
                string m = reader.GetString(3);
                string t = reader.GetString(4);


                messages.Add(new Message { sender = s, recipient = r, message = m, time = t });
            }
            reader.Close();


            return View(messages);
        }

        private static string nname;
        public IActionResult Login()
        {
            MySqlConnection connection = new MySqlConnection("SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177");
            connection.Open();

            MySqlCommand command = new MySqlCommand();
            command.Connection = connection;
            command.CommandText = "SELECT * FROM customers";

            MySqlDataReader reader = command.ExecuteReader();
            List<User> l = new List<User>();

            while (reader.Read())
            {
                int u_id = reader.GetInt32(0);
                string u_name = reader.GetString(1);
               

                l.Add(new User { Id = u_id, Name = u_name});
            }
            reader.Close();

            nname = Request.Form["name"];            

            for (int i = 0; i < l.Count; ++i)
            {
                if (l.ElementAt(i).Name.Equals(nname))
                {
                    return View(l);
                }

            }

            MySqlCommand cmd = new MySqlCommand("insert into web_app.customers (name) " +
                "values ('" +  nname + "')", connection);
            cmd.ExecuteNonQuery();

            l.Add(new User() { Id = l.Count, Name = nname });


            return View(l);
        }

        public IActionResult Send()
        {
            string interlocutor = Request.Form["name"];
            string message = Request.Form["message"];
            string time = DateTime.Now.ToString();
            MySqlConnection connection = new MySqlConnection("SERVER=localhost;port=3306;DATABASE=web_app;UID=root;PASSWORD=221177");
            connection.Open();
            MySqlCommand cmd = new MySqlCommand("insert into `web_app`.`messenger` (`name`, `interlocutor`, `message`, `time`) " +
                "VALUES('" + nname +"','" + interlocutor + "', '" + message + "', '" + time + "') ", connection);
            cmd.ExecuteNonQuery();


            

           
            
            cmd.CommandText = "SELECT * FROM customers";

            MySqlDataReader reader = cmd.ExecuteReader();
            List<User> l = new List<User>();

            while (reader.Read())
            {
                int u_id = reader.GetInt32(0);
                string u_name = reader.GetString(1);


                l.Add(new User { Id = u_id, Name = u_name });
            }
            reader.Close();


 



            return View(l);
        }

        public IActionResult Privacy()
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