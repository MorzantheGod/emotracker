using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using MonoTouch.CoreBluetooth;

namespace Emotracker.Core
{
	public class BluetoothManager
	{
		private static readonly int SCAN_DELAY = 10000;

		private CBCentralManager centralManager;
		private Boolean isScanning;
		private List<CBPeripheral> discoveredDevices;

		public event EventHandler ScanTimeoutElapsed = delegate {};

		public BluetoothManager ()
		{
			centralManager = new CBCentralManager (MonoTouch.CoreFoundation.DispatchQueue.CurrentQueue);
		}

		public async Task BeginScanningForDevices()
		{
			Console.WriteLine ("BluetoothLEManager: Starting a scan for devices.");

			// clear out the list
			this.discoveredDevices = new List<CBPeripheral> ();

			// start scanning
			this.isScanning = true;
			centralManager.ScanForPeripherals (serviceUuids:null);

			// in 10 seconds, stop the scan
			await Task.Delay (SCAN_DELAY);

			// if we're still scanning
			if (this.isScanning) {
				Console.WriteLine ("BluetoothLEManager: Scan timeout has elapsed.");
				this.centralManager.StopScan ();
				this.ScanTimeoutElapsed (this, new EventArgs ());
			}
		}
	}
}

