using System;

namespace Emotracker.Core
{
	[Serializable]
	public class WebMessage
	{
		public String State { get; set; }
		public String Message { get; set; }
		public Object Result { get; set; }
	}
}

