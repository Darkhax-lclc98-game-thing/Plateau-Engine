package plateau.engine.security;

import java.security.Permission;

public class PlateauSecurityManager extends SecurityManager {
	@Override
	public void checkPermission(Permission perm) {
		String permName = perm.getName() != null ? perm.getName() : "missing";
		if (permName.startsWith("exitVM")) {
			Class<?>[] context = getClassContext();
			String invokingClass = context.length > 3 ? context[4].getName() : "none";
			if (!(invokingClass.startsWith("plateau.engine."))) {
				throw new SecurityException("Cannot exit from outside of the Plateau engine!");
			}
		} else if ("setSecurityManager".equals(permName)) {
			throw new SecurityException("Cannot replace the Plateau SecurityManager");
		}
		return;
	}
}
