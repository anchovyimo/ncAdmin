package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import static views.html.index.render;

public class Application extends Controller {

    public static Result index() {
        return ok(render());
    }

    public static Result login() {
        return ok(login.render(Form.form(User.class)));
    }

    public static Result authenticate() {
        Form<User> loginForm = Form.form(User.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        }
        session().clear();
        session("name", loginForm.get().name);
        return redirect(routes.Application.index());
    }

}




