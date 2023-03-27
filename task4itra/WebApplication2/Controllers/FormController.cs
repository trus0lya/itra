using Microsoft.AspNetCore.Mvc;
using System.ComponentModel;

namespace WebApplication2.Controllers
{
    public class FormController : Controller
    {
        [HttpGet]
        public IActionResult Index()
        {
            return View();
        }

  /*      [HttpPost]
        public IActionResult Index(string firstName)
        {
            return Content($"Hello {firstName}");
        }*/
        [HttpPost, ValidateAntiForgeryToken]
        public IActionResult Index(FormModel model)
        {
            return Content($"Hello {model.FirstName}");
        }
    }
    public class FormModel
    {
        [DisplayName("First Name")]
        public string FirstName { get; set; }
    }
}
