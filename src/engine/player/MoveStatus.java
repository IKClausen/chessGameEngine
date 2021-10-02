package engine.player;

// Might make more sense in another package? 
public enum MoveStatus {
	DONE {
		@Override
		boolean isDone() {
			// TODO Auto-generated method stub
			return false;
		}
	}; 
	abstract boolean isDone();
}
