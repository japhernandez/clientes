package com.company.apirest.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	/**
	 * @param nombreFoto
	 * @return
	 * @throws MalformedURLException
	 */
	public Resource cargar(String nombreFoto) throws MalformedURLException;

	/**
	 * @param archivo
	 * @return
	 * @throws IOException
	 */
	public String copiar(MultipartFile archivo) throws IOException;

	/**
	 * @param nombreFoto
	 * @return
	 */
	public boolean eliminar(String nombreFoto);

	/**
	 * @param nombreFoto
	 * @return
	 */
	public Path getPath(String nombreFoto);
}
