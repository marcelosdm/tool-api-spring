package com.marcelo.tools.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.tools.entity.Tool;
import com.marcelo.tools.exception.ToolNotFoundException;
import com.marcelo.tools.repository.ToolRepository;

@RestController
@RequestMapping("/tools")
public class ToolController {

	
	@Autowired
	ToolRepository toolRepository;
	
	/**
	 * Get all tools
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Tool>> getAllTools() {
		return ResponseEntity.status(HttpStatus.OK).body(toolRepository.findAll());
	}
	
	/**
	 * Create a new tool
	 * @param tool
	 * @return
	 */
	@PostMapping
	public Tool createTool(@Valid @RequestBody Tool tool) {
		return toolRepository.save(tool);
	}
	
	/**
	 * Get a tool by id
	 * @param toolId
	 * @return
	 * @throws ToolNotFoundException
	 */
	@GetMapping("{id}")
	public Tool getToolById(@PathVariable("id") Long toolId) throws ToolNotFoundException {
		return toolRepository.findById(toolId)
				.orElseThrow(() -> new ToolNotFoundException(toolId));
	}
	
	/**
	 * Update a tool
	 * @param toolId
	 * @param toolDetails
	 * @return
	 * @throws ToolNotFoundException
	 */
	@PutMapping("{id}")
	public Tool updateTool(@PathVariable("id") Long toolId,
			@Valid @RequestBody Tool toolDetails) throws ToolNotFoundException {
		Tool tool = toolRepository.findById(toolId)
				.orElseThrow(() -> new ToolNotFoundException(toolId));
		
		tool.setName(toolDetails.getName());
		tool.setDescription(toolDetails.getDescription());
		tool.setCategory(toolDetails.getCategory());
		tool.setTags(toolDetails.getTags());
		
		Tool updatedTool = toolRepository.save(tool);
		
		return updatedTool;
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteTool(@PathVariable("id") Long toolId) throws ToolNotFoundException {
		Tool tool = toolRepository.findById(toolId)
				.orElseThrow(() -> new ToolNotFoundException(toolId));
		
		toolRepository.delete(tool);
		return ResponseEntity.ok().build();
	}
}
