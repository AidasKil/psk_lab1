package rest;

import entities.Category;
import logging.ILogger;
import logging.LogMessage;
import lombok.SneakyThrows;
import persistence.CategoryDAO;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/categories")
public class CategoriesController {
	@Inject
	private CategoryDAO categoryDAO;

	@Inject
	ILogger logger;

	@Inject
	private CategoriesController controller;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CategoryContract> get() {
		return categoryDAO.loadAll().stream().map(x -> {
			CategoryContract category = new CategoryContract();
			category.setId(x.getId());
			category.setName(x.getName());
			return category;
		}).collect(Collectors.toList());
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public CategoryContract create(CategoryContract categoryContract) {
		Category category = new Category();
		category.setName(categoryContract.getName());
		categoryDAO.persist(category);

		CategoryContract response = new CategoryContract();
		response.setName(category.getName());
		response.setId(category.getId());
		return response;
	}

	@Resource
	private TransactionSynchronizationRegistry tx;

	@SneakyThrows
	@Path("{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response update(@PathParam("id") int id, CategoryContract categoryContract) {
		try {
			Category category = categoryDAO.findOne(id);
			if (category == null)
				return Response.status(Response.Status.NOT_FOUND).build();

			category.setName(categoryContract.getName());

			Thread.sleep(1000);

			categoryDAO.update(category);

			CategoryContract responseBody = new CategoryContract();
			responseBody.setName(category.getName());
			responseBody.setId(category.getId());
			return Response.ok().entity(responseBody).build();
		} catch (OptimisticLockException ole) {
			logger.log(new LogMessage("Received OLE exception while updating"));
			return Response.status(Response.Status.CONFLICT).build();
		}
	}
}
