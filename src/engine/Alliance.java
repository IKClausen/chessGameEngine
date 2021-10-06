package engine;

import engine.player.BlackPlayer;
import engine.player.Player;
import engine.player.WhitePlayer;

public enum Alliance {
 WHITE 
 {@Override
  public int getDirection() {
   // TODO Auto-generated method stub
		return -1;
	}

@Override
public boolean isWhite() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isBlack() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Player choosePlayer(final WhitePlayer whitePlayer,
		                   final BlackPlayer blackPlayer) {

	return whitePlayer;
}
 } ,       
       
 BLACK    
 {
	@Override
	public int getDirection() {
		return 1;
	}

	@Override
	public boolean isWhite() {
		return false;
	}

	@Override
	public boolean isBlack() {
		return true;
	}

	@Override
	public Player choosePlayer(final WhitePlayer whitePlayer, 
			                   final BlackPlayer blackPlayer) {
		// TODO Auto-generated method stub
		return blackPlayer;
	}
};
    public abstract int getDirection(); 
    public abstract boolean isWhite(); 
    public abstract boolean isBlack();
	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer); 
} 
