package com.booking.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

import com.booking.entity.HallEntity;
import com.booking.entity.dto.HallEntityRequestDTO;
import com.booking.exception.HallException;
import com.booking.service.HallService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    @Autowired
    private HallService hallService;

    /**
     * Retrieves a HallEntity by its unique identifier.
     *
     * @param id the unique identifier of the hall to retrieve
     * @return the HallEntity if found, or {@code null} if no hall exists with the
     *         given id
     */
    @GetMapping("/all")
    @Operation(summary = "Get all halls", description = "Retrieves a list of all halls in the system.", tags = {
            "Hall Management" }, responses = {
                    @ApiResponse(responseCode = "200", description = "List of all halls retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "No halls found")
            })
    public ResponseEntity<?> getAllHalls() {
        return ResponseEntity.ok(hallService.getAllHalls());
    }

    /**
     * Retrieves the details of a hall by its unique identifier.
     *
     * @param id the unique identifier of the hall to retrieve
     * @return a ResponseEntity containing the hall details if found, or an
     *         appropriate error response
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get hall by ID", description = "Retrieves the details of a hall by its unique identifier.", tags = {
            "Hall Management" }, parameters = {
                    @Parameter(name = "id", description = "ID of the hall to retrieve", required = true, example = "1")
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Hall details retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Hall not found")
            })
    public ResponseEntity<?> getHallById(@PathVariable int id) {
        return ResponseEntity.ok(hallService.getHallById(id));
    }

    /**
     * Deletes a hall with the specified ID.
     *
     * @param id the ID of the hall to be deleted
     * @return a ResponseEntity containing a success message upon successful
     *         deletion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a hall by ID", description = "Deletes a hall with the specified ID from the system.", tags = {
            "Hall Management" }, parameters = {
                    @Parameter(name = "id", description = "ID of the hall to be deleted", required = true, example = "1")
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "El salon con el id ha sido eliminado"),
                    @ApiResponse(responseCode = "404", description = "El salon con id no existe")
            })
    public ResponseEntity<?> deleteHall(@PathVariable int id) {
        try {
            String response = hallService.deleteHall(id);
            return ResponseEntity.ok(Map.of("message", response));
        } catch (HallException e) {
            return ResponseEntity.status(Response.SC_BAD_REQUEST).body(
                    Map.of("error", "Error deleting hall", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR).body("Error of server: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP POST request to add a new hall.
     *
     * @param hall The HallEntity object containing the details of the hall to be
     *             added.
     * @return A ResponseEntity containing the saved HallEntity object with a status
     *         of 201 (Created)
     *         if the operation is successful, or an error message with a status of
     *         500 (Internal Server Error)
     *         if an exception occurs.
     */
    @PostMapping
    @Operation(summary = "Register a new hall", description = "Creates a new hall with the provided details. The hall must have a unique name and a valid capacity.", tags = {
            "Hall Management" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON object with the details of the hall to be created.", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = HallEntity.class), examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(name = "Example Hall", summary = "Typical hall creation request", value = """
                            {
                              "id": 1,
                              "name": "RECO",
                              "location": "Bloque B0",
                              "status": "A",
                              "description": "Descripción personalizada",
                              "capacity": 100
                            }
                            """)
            })), responses = {
                    @ApiResponse(responseCode = "201", description = "Hall successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HallEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid hall data or business rule violation", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<?> addHall(@RequestBody HallEntityRequestDTO hall) {
        try {
            HallEntity savedHall = hallService.saveHall(hall);
            return ResponseEntity.status(201).body(
                    Map.of("message", "Salon creado correctamente", "hall", savedHall)
            );
        } catch (HallException e) {
            return ResponseEntity.status(Response.SC_BAD_REQUEST).body(
                    Map.of("error", "Error saving hall", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR).body("Error of server: " + e.getMessage());
        }
    }
}
