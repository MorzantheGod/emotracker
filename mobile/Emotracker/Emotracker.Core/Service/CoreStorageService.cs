using System;
using SQLite;
using Mono.Data.Sqlite;
using System.Data;

namespace Emotracker.Core
{
	public class CoreStorageService
	{
		private SQLiteConnection conn;

		public CoreStorageService ()
		{
			string folder = Environment.GetFolderPath (Environment.SpecialFolder.Personal);
			conn = new SQLiteConnection (System.IO.Path.Combine (folder, "userprefs.db"));
			conn.CreateTable<UserEntity> ();
				
		}

		public void saveUserData(UserDTO dto)
		{
			UserEntity save = new UserEntity ();
			save.fullName = dto.fullName;
			save.userName = dto.userName;
			save.tokenKey = dto.tokens [0].Key;
			save.tokenValue = dto.tokens [0].Token;
			save.Id = 1;

			UserEntity user = getUserEntity ();
			if (user == null) {
				conn.Insert (save);
			} else {
				conn.Update (save);
			}
		}

		public UserEntity getUserEntity()
		{
			var table = conn.Table<UserEntity> ();
			foreach (var s in table) {
				if (s.Id == 1) {
					return s;
				}
			}

			return null;
		}

		public UserDTO getUserData()
		{
			UserEntity entity = getUserEntity ();
			if (entity == null) {
				return null;
			}

			UserDTO dto = new UserDTO ();
			dto.fullName = entity.fullName;
			dto.userName = entity.userName;
			dto.email = entity.email;

			dto.tokens = new System.Collections.Generic.List<TokenDTO> ();
			TokenDTO token = new TokenDTO ();
			token.Key = entity.tokenKey;
			token.Token = entity.tokenValue;
			dto.tokens.Add (token);

			return dto;
		}

	}
}

