package com.gensoft.saasapi.pojo.usergroup;

import javax.validation.constraints.NotNull;

/**
 * Created by alan on 16-5-25.
 */
public class UserGroupEntity {
	
	    private String name;
	    private String descipt;
	    private long tagId;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescipt() {
			return descipt;
		}
		public void setDescipt(String descipt) {
			this.descipt = descipt;
		}
		public long getTagId() {
			return tagId;
		}
		public void setTagId(long tagId) {
			this.tagId = tagId;
		}
	    
	    
}
