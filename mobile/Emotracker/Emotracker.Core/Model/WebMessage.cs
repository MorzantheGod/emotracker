using System;

namespace Emotracker.Core
{
	[Serializable]
	public class WebMessage
	{
		public static readonly string OK_RESULT = "OK";
		public static readonly string ERROR_RESULT = "ERROR";

		public String State { get; set; }
		public String Message { get; set; }
		public Object Result { get; set; }
	}
}

