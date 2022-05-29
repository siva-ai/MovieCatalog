package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {
    String url="http://localhost:8083/ratingsdata/moviedetails/";

    @Autowired
    private RestTemplate restTemplate;

    public CloseableHttpClient client= HttpClients.createDefault();


    @RequestMapping(value="/{userId}",method=RequestMethod.GET)
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        UserRating userRating=restTemplate.getForObject("http://localhost:8083/ratingsdata/user/"+userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(),Movie.class);
                            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());
    }


    @RequestMapping(value = "/moviedetails/{movieId}",method= RequestMethod.GET)
    public MovieDetails getRating(@PathVariable("movieId") String movieId) throws IOException {
        CloseableHttpResponse response=null;
        //MovieDetails moviedetails = restTemplate.getForObject("http://localhost:8083/ratingsdata/moviedetails/"+movieId,MovieDetails.class);


       try{
           HttpPost httpPost= new HttpPost(url+movieId);

           response= client.execute(httpPost);
           ObjectMapper mapper= new ObjectMapper();
           String res= EntityUtils.toString(response.getEntity());


           MovieDetails movieDetails= mapper.readValue(res,MovieDetails.class);

           //System.out.println(client);
           return movieDetails;
//Objectmapper--- mapper is used to modify values in one format to required formats .
           // Objectmapper's readvalue method has a lot number of overidden methods for every usecase.
           /*closeablehttp client--- may be it acts like web browser through which we can execute different types of
           request methods, client has execute method through which we can execute */



       }
       catch(IOException e) {
           throw new RuntimeException();
       }
       finally{
           if(client != null)
           {
               //client.close();
           }
           if(response!=null)
           {
               //response.close();
           }

        //return moviedetails;
       }
    }

}
