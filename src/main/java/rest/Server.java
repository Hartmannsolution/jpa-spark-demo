/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookDTO;
import entities.Book;
import facades.BookFacade;
import javax.persistence.Persistence;
import static spark.Spark.*;

/**
 *
 * @author thomas
 */
public class Server {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final BookFacade BF = BookFacade.getFacade(Persistence.createEntityManagerFactory("pu"));

    public static void main(String[] args) {

        port(8081); // Spark will run on port 8081 (default is 4567)

        path("/api", () -> {
            before("/*", (q, a) -> System.out.println("Recieved api call ...")); //log.info("Received api call")
            path("/book", () -> {
                get("/all",(request, response)->{
                    return GSON.toJson(BF.getAllBook());
                });
                post("/add", (request, response) -> {
                    response.type("application/json");
                    BookDTO bd = new Gson().fromJson(request.body(), BookDTO.class);
                    BookDTO created = BF.create(bd);

                    return GSON.toJson(created);
                });
                put("/change",(request, response) -> {
                    response.type("application/json");
                    BookDTO bd = new Gson().fromJson(request.body(), BookDTO.class);
                    BookDTO updated = BF.update(bd);

                    return GSON.toJson(updated);
                });
                delete("/remove", (request, response) -> {
                    response.type("application/json");
                    BookDTO bd = new Gson().fromJson(request.body(), BookDTO.class);
                    BookDTO created = BF.create(bd);

                    return GSON.toJson(created);
                });
            });
//            path("/username", () -> {
//                post("/add", UserApi.addUsername);
//                put("/change", UserApi.changeUsername);
//                delete("/remove", UserApi.deleteUsername);
//            });
        });
    }
}
