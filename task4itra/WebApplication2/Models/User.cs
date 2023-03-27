namespace WebApplication2.Models
{
    public class User
    {
        public int user_id { get; set; }
        public string user_name { get; set; }
        public string registration_date { get; set; }
        public string last_seen { get; set; }

        public string status { get; set; }

        public string password { get; set; }

        public bool isBlocked { get; set; }
        public bool isDeleted { get; set; }

    }
}
