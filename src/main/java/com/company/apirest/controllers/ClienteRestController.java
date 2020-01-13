package com.company.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.apirest.models.entity.Cliente;
import com.company.apirest.models.entity.Region;
import com.company.apirest.models.services.IClienteService;
import com.company.apirest.models.services.IUploadFileService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadService;

	/**
	 * URL /api/clientes
	 * 
	 * @return clientes
	 */
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	/**
	 * URL /api/clientes/page/2
	 * 
	 * @return clientes
	 */
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page) {
		return clienteService.findAll(PageRequest.of(page, 1));
	}

	/**
	 * URL /api/clientes/1
	 * 
	 * @param id
	 */
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cliente = clienteService.findById(id);
			if (cliente == null) {
				response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en el sistema!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * URL /api/clientes
	 * 
	 * @param cliente
	 * @return
	 */
	@PostMapping("/clientes")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {

		Cliente nuevoCliente = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (FieldError err : result.getFieldErrors()) {
				errors.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			}
			response.put("error", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			nuevoCliente = clienteService.save(cliente);
			response.put("mensaje", "El cliente se ha creado con exito");
			response.put("cliente", nuevoCliente);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * URL /api/clientes/1
	 * 
	 * @param cliente
	 * @param id
	 * @return
	 */
	@PutMapping("/clientes/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		// Buscamos el cliente por el id que llega como parametro
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdate = null;
		Map<String, Object> response = new HashMap<>();

		// Validamos todas las reglas del modelo para pasarlas en un arrayList al
		// cliente
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("error", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			// Si no existe retornamos un error
			if (clienteActual == null) {
				response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en el sistema!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			// Parametros que llegan en el cuerpo de la peticion
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreatedAt(cliente.getCreatedAt());
			clienteActual.setRegion(cliente.getRegion());

			// Esta variable guarda el objeto cliente actualizado
			clienteUpdate = clienteService.save(clienteActual);

			// Retornamos el mensale de exito con la variable que tiene el objeto cliente
			response.put("mensaje", "El cliente se ha actualizado con exito");
			response.put("cliente", clienteUpdate);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al atualzar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * URL /api/clientes/1
	 * 
	 * @param id
	 */
	@DeleteMapping("/clientes/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			// Buscamos el cliente para borrar la imagen
			Cliente cliente = clienteService.findById(id);

			// Esta variable guarda el nombre del archivo que hay en base de datos
			// para borrarlo cuando se ingrese una nueva imagen del cliente
			String nombreFotoAnterior = cliente.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			clienteService.delete(id);
			response.put("mensaje", "El cliente se ha eliminado con exito");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * URL /api/clientes/upload
	 * 
	 * @param archivo
	 * @param id
	 * @return
	 */
	@PostMapping("/clientes/upload")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		// Buscamos el cliente para asignar la imagen
		Cliente cliente = clienteService.findById(id);

		// Validamos que el archivo no este vacio
		if (!archivo.isEmpty()) {

			String nombreArchivo = null;

			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del cliente de la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// Esta variable guarda el nombre del archivo que hay en base de datos
			// para borrarlo cuando se ingrese una nueva imagen del cliente
			String nombreFotoAnterior = cliente.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			// Finalizamos guardando el archivo nuevo
			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "Haz subido correctamente la imagen: " + nombreArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	/**
	 * URL /api/clientes/uploads/img/nombreFoto.jpg
	 * 
	 * @param archivo
	 * @param id
	 * @return
	 */
	@GetMapping("/clientes/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable("nombreFoto") String nombreFoto) {

		Resource recurso = null;

		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders cabecera = new HttpHeaders();

		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

	/**
	 * URL /api/clientes/regiones
	 * 
	 * @return
	 */
	@GetMapping("/clientes/regiones")
	@Secured({"ROLE_ADMIN"})
	public List<Region> listarRegiones() {
		return clienteService.findAllRegiones();
	}
}
