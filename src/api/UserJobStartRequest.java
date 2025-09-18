package api;

public class UserJobStartRequest {
	//default delimiter
	public String delimiter;

	public UserJobStartRequest() {
		this.delimiter = ",";
	}

	//custom delimiter from user
	public UserJobStartRequest(String delimiter) {
		this.delimiter = delimiter;
	}

	//get method for user delimiter
	public String getDelimiter() {
		return delimiter;
	}
}