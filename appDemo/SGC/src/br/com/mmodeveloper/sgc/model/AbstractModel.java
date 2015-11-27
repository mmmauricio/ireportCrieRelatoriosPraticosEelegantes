package br.com.mmodeveloper.sgc.model;

import java.io.Serializable;

public abstract class AbstractModel<Id extends Serializable> implements
		Serializable {
	private static final long serialVersionUID = 1L;

	public abstract Id getId();
	public abstract void setId(Id id);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (getId() instanceof Number) {
			Number n = (Number) getId();
			result = prime * result + n.intValue();
		} else {
			result = super.hashCode();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			AbstractModel other = (AbstractModel) obj;
			if (this.getId() != null) {
				if (!getId().equals(other.getId())) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
}