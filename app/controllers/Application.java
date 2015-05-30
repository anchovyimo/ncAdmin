package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.*;
import static play.data.Form.*;
import models.*;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(index.render(
                User.find.byId(request().username())
        ));
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

    public static Result addUser() {
        User user = User.find.where().eq("name", "admin").findUnique();
        if (user == null) {
            User.create("admin", "password123");
        }
        return redirect(routes.Application.login());
    }

    public static Result logout() {
        session().clear();
        return redirect(routes.Application.login());
    }

}
