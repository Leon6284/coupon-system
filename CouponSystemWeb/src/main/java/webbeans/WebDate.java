package webbeans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WebDate implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public WebDate() {
		
	}

	@Override
	public String toString() {
		return "WebDate [date=" + date + "]";
	}
	
	
}
