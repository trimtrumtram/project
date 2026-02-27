package com.crudapi.crud.controller;

import com.common.common.enums.sort.SortDirection;
import com.common.common.enums.sort.EmployeeSortField;
import com.crudapi.crud.dto.employee.CreateEmployeeDTO;
import com.crudapi.crud.dto.employee.EmployeeFilterDTO;
import com.crudapi.crud.dto.employee.EmployeeResponseDTO;
import com.crudapi.crud.dto.employee.UpdateEmployeeDTO;
import com.crudapi.crud.mapper.filterMapper.EmployeeFilterMapper;
import com.crudapi.crud.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeFilterMapper employeeFilterMapper;
    private EmployeeResponseDTO employeeDto;

    @PostMapping
    @Operation(
            summary = "Create a new employee",
            description = "Creating a new employee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee created successfully"),
                    @ApiResponse(responseCode = "400", description = "Employee is not created")
            }
    )
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody @Valid CreateEmployeeDTO dto) {
        employeeDto = employeeService.createEmployee(dto);
        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing employee",
            description = "Updating an existing employee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Employee is not updated")
            }
    )
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody @Valid UpdateEmployeeDTO dto) {
        employeeDto = employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok(employeeDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an existing employee",
            description = "Deleting an existing employee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Employee is not deleted")
            }
    )
    public boolean deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return true;
    }

    @GetMapping
    @Operation(
            summary = "Get all employees",
            description = "Getting all employees",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employees found successfully"),
                    @ApiResponse(responseCode = "404", description = "Employees not found")
            }
    )
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(required = false) String firstNameFilter,
            @RequestParam(required = false) String lastNameFilter,
            @RequestParam(required = false) String roleFilter,
            @RequestParam(required = false) String emailFilter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "LASTNAME") EmployeeSortField sortBy,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection)
    {
        EmployeeFilterDTO filter = employeeFilterMapper.toDTO(
                firstNameFilter,
                lastNameFilter,
                roleFilter,
                emailFilter,
                page,
                size,
                sortBy,
                sortDirection
        );
        Page<EmployeeResponseDTO> employees = employeeService.getAllEmployees(filter);
        return ResponseEntity.ok(employees);
    }
}
