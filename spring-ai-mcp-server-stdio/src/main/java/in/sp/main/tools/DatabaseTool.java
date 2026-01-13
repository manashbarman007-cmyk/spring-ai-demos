package in.sp.main.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entity.ShoppingCart;
import in.sp.main.repo.ShoppingCartRepo;

@Service
public class DatabaseTool {

	@Autowired
	private ShoppingCartRepo repo;
	
	@Tool(description = "This tool inserts the ShoppingCart object in the database")
	public ShoppingCart insertInCart(
			@ToolParam(description = "The ShoppingCart to be interted", required = true) ShoppingCart shoppingCart
			) {
		shoppingCart.setId(null); // it is auto generated in the database
		return repo.save(shoppingCart);
	}
	
	@Tool(description = "This tool fetches the ShoppingCart object from the database by the customers name")
	public ShoppingCart getCartDetailsByCustomer(
			@ToolParam(description = "Name of the customer to be obtained", required = true) String customer
			) {
		return repo.findByCustomer(customer).orElseThrow(() -> new RuntimeException("No such item"));
	}
	
}
