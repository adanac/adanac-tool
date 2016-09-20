package com.adanac.tool.entity;

import java.io.Serializable;

/**
 * 技能
 */
public class SkillDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1801211555303494889L;
	private String sid;
	private String sname;
	private int slevel;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getSlevel() {
		return slevel;
	}

	public void setSlevel(int slevel) {
		this.slevel = slevel;
	}

	@Override
	public String toString() {
		return "Skill [sid=" + sid + ", sname=" + sname + ", slevel=" + slevel + "]";
	}

}
