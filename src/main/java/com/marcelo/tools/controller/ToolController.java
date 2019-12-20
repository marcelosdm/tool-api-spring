package com.marcelo.tools.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.tools.entity.Tool;
import com.marcelo.tools.error.NotFoundError;
import com.marcelo.tools.repository.ToolRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tools")
public class ToolController {

	@Autowired
	ToolRepository toolRepository;

	/**
	 * Get all tools
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Tool>> getAllTools() {
		return ResponseEntity.status(HttpStatus.OK).body(toolRepository.findAll());
	}

	/**
	 * Get a tool by id
	 * 
	 * @param toolId
	 * @return
	 * @throws NotFoundError
	 */
	@GetMapping("{id}")
	public ResponseEntity<Tool> getToolById(@PathVariable("id") Long toolId) throws NotFoundError {
		return ResponseEntity.status(HttpStatus.OK)
				.body(toolRepository.findById(toolId)
						.orElseThrow(() -> new NotFoundError("No tool found with the id " + toolId)));
	}

	/**
	 * Create a new tool
	 * 
	 * @param tool
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Tool> createTool(@Valid @RequestBody Tool tool) {
		return ResponseEntity.status(HttpStatus.OK).body(toolRepository.save(tool));
	}

	/**
	 * Update a tool
	 * 
	 * @param toolId
	 * @param toolDetails
	 * @return
	 * @throws NotFoundError
	 */
	@PutMapping("{id}")
	public ResponseEntity<Tool> updateTool(@PathVariable("id") Long toolId, @Valid @RequestBody Tool toolDetails)
			throws NotFoundError {
		Tool tool = toolRepository.findById(toolId)
				.orElseThrow(() -> new NotFoundError("No tool found with the id " + toolId));

		tool.setName(toolDetails.getName());
		tool.setDescription(toolDetails.getDescription());
		tool.setCategory(toolDetails.getCategory());
		tool.setTags(toolDetails.getTags());

		Tool updatedTool = toolRepository.save(tool);

		return ResponseEntity.status(HttpStatus.OK).body(updatedTool);
	}

	/**
	 * Delete a tool
	 * 
	 * @param toolId
	 * @return
	 * @throws NotFoundError
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteTool(@PathVariable("id") Long toolId) throws NotFoundError {
		Tool tool = toolRepository.findById(toolId)
				.orElseThrow(() -> new NotFoundError("No tool found with the id " + toolId));

		toolRepository.delete(tool);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
