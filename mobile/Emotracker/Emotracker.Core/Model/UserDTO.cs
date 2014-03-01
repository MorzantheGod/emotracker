using System;
using System.Collections.Generic;

namespace Emotracker.Core
{
	[Serializable]
	public class UserDTO
	{
		public string id { get; set; }
		public string fullName { get; set; }
		public string userName { get; set; }
		public string email { get; set; }

		public List<TokenDTO> tokens { get; set; }
	}
}

