using System;
using PerpetualEngine.Storage;

namespace Emotracker.Core
{
	public class StorageService
	{
		private static readonly string STORAGE_NAME = "userprefs";
		private static SimpleStorage storage;

		public StorageService ()
		{
			storage = SimpleStorage.EditGroup(STORAGE_NAME);
		}

		public static void saveUserData(UserDTO dto)
		{
			getStorage().Put ("user", dto);
		}

		public static UserDTO getUserData()
		{
			UserDTO dto = getStorage().Get<UserDTO> ("user");
			return dto;
		}

		private static SimpleStorage getStorage() 
		{
			if (storage == null) {
				return SimpleStorage.EditGroup(STORAGE_NAME);
			}
			return storage;
		}
	}
}

