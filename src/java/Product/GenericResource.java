/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Product;

import entity.Product;
import entity.ProductList;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Khushdeep
 */
@Path("/products")
@RequestScoped
public class GenericResource {
    @Inject
    ProductList productList;
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(productList.toJSON()).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        return Response.ok(productList.get(id).toJSON()).build();
    }

    @POST
    @Consumes("application/json")
    public Response add(JsonObject json) {
        Response response;
        try{
            productList.add(new Product(json));
            response = Response.ok(productList.get(json.getInt("productID")).toJSON()).build();
        } catch(Exception ex){
            response = Response.status(500).build();
        }
        return response;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response set(@PathParam("id") int id, JsonObject json) {
        Response response;
        try{
            Product p = new Product(json);
            productList.set(id, p);
            response = Response.ok(productList.get(id).toJSON()).build();
        } catch(Exception ex){
            response = Response.status(500).build();
        }
        return response;
    }
    
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        Response response;
        try{
            productList.remove(id);
            response = Response.ok("Deleted product with id "+id).build();
        } catch(Exception ex){
            response = Response.status(500).build();
        }
        return response;
    }

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of Product.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
