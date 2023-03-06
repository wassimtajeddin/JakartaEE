package com.example.jakartaee.controller;
import com.example.jakartaee.dto.PersonDto;
import com.example.jakartaee.entity.Person;
import com.example.jakartaee.mapper.Mapper;
import com.example.jakartaee.repository.PersonRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/people")
public class PersonController {

    @Inject
    PersonRepository repository;

    @Inject
    Mapper mapper;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDto> getAll(@QueryParam("name") String name) {
        if (name == null)
            return mapper.map(repository.findAll());

        return mapper.map(repository.findAllByName(name));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns person object",
                    content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(responseCode = "404", description = "Id not found")})
    public Response getOne(@PathParam("id") int id) {
        var person = repository.findOne(id);
        if (person.isPresent())
            return Response.ok().entity(person.get()).build();
        throw new NotFoundException("Id: " + id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOne(@Valid Person person) {

        repository.insertPerson(person);
        return Response.created(URI.create("people/" + person.getId())).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteOne(@PathParam("id") int id) {
        repository.deletePerson(id);
    }


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updDate(@PathParam("id") int id, PersonDto person) {
        return Response.ok().entity(mapper.map(repository.update(id, mapper.map(person)))).build();
    }
}

