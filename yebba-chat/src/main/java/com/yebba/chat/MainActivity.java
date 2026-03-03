public class MainActivity extends AppCompatActivity {
    IYebbaServiceInterface mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IYebbaServiceInterface.Stub.asInterface(service);
            try {
                String user = mService.getActiveUser();
                Log.d("YEBBA_CHAT", "Logged in as: " + user);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent("com.yebba.services.BIND_CORE");
        intent.setPackage("com.yebba.services");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
}
