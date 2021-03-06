public interface IUnit {
	
	public void move();
	
	public int getX();
	
	public int getY();
	
	public String getName();
	
	public int getHealth();
	
	public boolean dead();
	
	public boolean isHit(IUnit unit);
	
	public void decreaseHP();
	
	void redirection();
}
