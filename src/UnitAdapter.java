/**
 * @author kamontat
 * @version 1.0
 * @since Thu 27/Apr/2017 - 10:51 AM
 */
public class UnitAdapter implements IUnit {
	private int hp;
	private LegacyUnit unit;
	
	public UnitAdapter(LegacyUnit unit, int hp) {
		this.unit = unit;
		this.hp = hp;
	}
	
	@Override
	public void move() {
		unit.move();
	}
	
	@Override
	public int getX() {
		return unit.getX();
	}
	
	@Override
	public int getY() {
		return unit.getY();
	}
	
	@Override
	public String getName() {
		return "Legacy Unit";
	}
	
	@Override
	public int getHealth() {
		return hp;
	}
	
	@Override
	public boolean dead() {
		return hp < 0;
	}
	
	@Override
	public boolean isHit(IUnit unit) {
		return Math.abs(getX() - unit.getX()) < LegacyUnit.SIZE && Math.abs(getY() - unit.getY()) < LegacyUnit.SIZE;
	}
	
	@Override
	public void decreaseHP() {
		hp--;
	}
	
	@Override
	public void redirection() {
		unit.redirect("x");
		unit.redirect("y");
		decreaseHP();
	}
}
