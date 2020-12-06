package alm.example.fancyfruitadmin.Activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.auth.FirebaseAuth;

import alm.example.fancyfruitadmin.Pojos.User;
import alm.example.fancyfruitadmin.Providers.AuthProvider;
import alm.example.fancyfruitadmin.Providers.UserProvider;
import alm.example.fancyfruitadmin.R;
import alm.example.fancyfruitadmin.Services.LocationService;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.Utils.Listeners.ConnectivityChangesListener;
import alm.example.fancyfruitadmin.Utils.Listeners.ConnectivityChangesNotifier;
import alm.example.fancyfruitadmin.databinding.LoginActivityBinding;

public class LoginActivity extends BaseActivity implements ConnectivityChangesListener {

    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth auth;
    private LoginActivityBinding binding;
    private UserProvider userProvider;
    private AuthProvider authProvider;
    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;
    private boolean hasConnectivity = Helper.checkInternet(this);

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        hasConnectivity = Helper.checkInternet(this);
        ConnectivityChangesNotifier.addListener(this);
        ConnectivityChangesNotifier.checkChanges(this);

        super.onCreate(savedInstanceState);
        addEventListeners();

        // PARAR SERVICIO DE LOCALIZACION
        if(isMyServiceRunning(LocationService.class)) {
            Intent locationService = new Intent(this, LocationService.class);
            stopService(locationService);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        auth = FirebaseAuth.getInstance();

        signOut();
    }

    @Override
    protected void onViewBind() {
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onServiceInit() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hasConnectivity = Helper.checkInternet(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasConnectivity = Helper.checkInternet(this);
    }

    @Override
    protected void onInitializeVariables() {
        userProvider = new UserProvider(this);
        authProvider = new AuthProvider(this);
    }

    private void signIn(View view) {
        if(hasConnectivity) {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            Snackbar.make(view, "No hay conexión a internet", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    private void signOut() {
        // Firebase sign out
        auth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Log.e(TAG, "Logged out correctly");
        });
    }

    // LOGIN DE FIREBASE
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AlertDialog dialog = Helper.loadingAlertBuilder(
                        "Cargando",
                        LoginActivity.this,
                        binding.getRoot()
                ).show();

                User user = userProvider.getByEmail(account.getEmail());

                Helper.showMessageAlert(
                        "Aviso",
                        "Para iniciar sesión con Google tenemos que saber la contraseña que " +
                                "tienes asignada dentro de la consola de administración",
                        this,
                        false,
                        () -> {

                            Helper.showInputAlert(
                                    "Introduce contraseña",
                                    this,
                                    binding.getRoot(),
                                    s -> {

                                        boolean isValid = authProvider.validatePassword(s.trim(), user.getPassword());

                                        if (isValid) {
                                            // GUARDAMOS LAS CREDENCIALES DE MI USUARIO PARA LA API
                                            Helper.storeCredentials(this, user.getUuid(), user.getUsername(), s.trim());
                                            startActivity(
                                                    new Intent(this, MainActivity.class)
                                            );
                                            finish();
                                        } else {
                                            Snackbar.make(
                                                    binding.getRoot(),
                                                    "La contraseña introducida no se corresponde con tus credenciales de registro",
                                                    BaseTransientBottomBar.LENGTH_LONG
                                            ).show();
                                        }
                                        dialog.cancel();
                                        return null;
                                    }
                            );
                            dialog.cancel();
                            return null;
                        });

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }

    }

    /*
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential: success");
                FirebaseUser firebaseUser = auth.getCurrentUser();

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential: failure", task.getException());
                Snackbar.make(binding.getRoot(), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                //updateUI(null);
            }
        });
    }
     */

    private void addEventListeners() {
        binding.googleSignInButton.setOnClickListener(this::signIn);
    }

    private void noConnection() {
        if(getWindow().getDecorView().isShown()) {
            Helper.showMessageAlert("Aviso", "No se dispone de conexión a internet", this);
        }
    }

    @Override
    public void onConnectivityChanges(boolean hasConnectivity) {
        this.hasConnectivity = hasConnectivity;
        if (!hasConnectivity) noConnection();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
