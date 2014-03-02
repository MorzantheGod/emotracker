using System;
using SQLite;
using System.Collections.Generic;

namespace Emotracker.Core
{
	public class UserEntity
	{
		[PrimaryKey]
		public int Id { get; set; }

		[MaxLength(100)]
		public string fullName { get; set; }

		[MaxLength(100)]
		public string userName { get; set; }

		[MaxLength(100)]
		public string email { get; set; }

		public string tokenKey { get; set; }
		public string tokenValue { get; set; }

		public UserEntity ()
		{
		}
	}
}

