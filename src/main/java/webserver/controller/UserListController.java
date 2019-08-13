package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.converter.HttpFileConverter;
import webserver.http.AbstractControllerStructor;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserListController extends AbstractControllerStructor {

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        try {
            HttpResponse response = HttpResponse.ok(httpRequest);


            String readFileContent = HttpFileConverter.readFile(httpRequest.getUrlPath() + HttpConverter.HTML_FILE_NAMING);

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.
                    compileInline(readFileContent);
            List<User> userList = DataBase.findAll()
                    .stream().collect(Collectors.toList());
            String remakeContent = template.apply(userList);

            response.initResultBody(httpRequest.getUrlPath(), remakeContent);
            return response;
        }catch (Exception e){
            return HttpResponse.pageNotFound(httpRequest);
        }
    }

}
