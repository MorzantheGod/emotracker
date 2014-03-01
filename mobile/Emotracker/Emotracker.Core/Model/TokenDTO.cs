using System;

namespace Emotracker.Core
{
	[Serializable]
	public class TokenDTO
	{
		public string Id { get; set; }
		public string Key { get; set; }
		public string Token { get; set; }
		public string Valid { get; set; }
	}
}

