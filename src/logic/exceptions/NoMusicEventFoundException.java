package logic.exceptions;

public class NoMusicEventFoundException extends NoFoundException{

	private static final long serialVersionUID = -5854007702325795953L;

	public NoMusicEventFoundException(String message) {
		super(message);
	}
}