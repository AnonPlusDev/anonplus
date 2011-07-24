package com.anonplus;

import java.util.ArrayList;
import java.util.List;

public class MimeTypeList {
	public List<MimeType> list = new ArrayList<MimeType>();
	
	public MimeType findByExtension(String ext)
	{
		ext = ext.toLowerCase();
		Global.message("Ext: " + ext);
		for(int i = 0; i < list.size(); ++i)
		{
			MimeType mt = list.get(i);
			for(int j = 0; j < mt.extensions.size(); ++j)
				if(mt.extensions.get(j).toLowerCase().equals(ext) )
					return mt;
		}
		
		return null;
	}
}
