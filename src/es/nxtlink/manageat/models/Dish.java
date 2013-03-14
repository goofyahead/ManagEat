package es.nxtlink.manageat.models;

public class Dish {
	
	private String id;
	private String name;
	private String description;
	private float price;
	private String image;
	private String video;
	private boolean demo;
	
	public Dish(String id, String name, String description, float price,
			String image, String video, boolean demo) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.image = image;
		this.video = video;
		this.demo = demo;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public float getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}

	public String getVideo() {
		return video;
	}

	public boolean isDemo() {
		return demo;
	}

	
}
