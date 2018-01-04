package gj.spacebattle.delegate;

import gj.spacebattle.entity.Shoot;

public interface ShootEngineDelegate {
	public void createShoot(
            Shoot shoot);

	public void removeShoot(Shoot shoot);
}
