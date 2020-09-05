package enums;

public enum Resource {

	SMOKE("smoke/"),
	REGRESSION("regression/");
	
	String resource;
	
	Resource(String resource) {
		this.resource = resource;
	}
	
	public String getResource() {
		return resource;
	}
}
