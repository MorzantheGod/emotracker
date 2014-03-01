using System;

namespace Emotracker.Core
{
	[Serializable]
	public class OperationResult
	{
		public Boolean Result { get; set; }
		public String Message { get; set; }
		public Object Content { get; set; }

		public OperationResult()
		{
		}

		public OperationResult (Boolean res, String mes)
		{
			this.Result = res;
			this.Message = mes;
		}
	}
}

