package com.example.att23;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Sun;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AR extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        Intent intent = getIntent();
        id = intent.getStringExtra("key");

        String name = "out" + id + ".glb";

        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child(name);

        ArFragment arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        try {
            File file = File.createTempFile("out", "glb");

            modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    buildModel(file);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            List<Node> children = new ArrayList<>(arFragment.getArSceneView().getScene().getChildren());

            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());

            if(children.size() >= 1)
            {
                for (Node node : children) {
                    if (node instanceof AnchorNode) {
                        if (((AnchorNode) node).getAnchor() != null) {
                            ((AnchorNode) node).getAnchor().detach();
                        }
                    }
                    if (!(node instanceof Camera) && !(node instanceof Sun)) {
                        node.setParent(null);
                    }
                }
            }

            //anchorNode.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);

            anchorNode.setParent(arFragment.getArSceneView().getScene());

            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
            transformableNode.setParent(anchorNode);

            transformableNode.setRenderable(renderable);

            //transformableNode.getScaleController().setMinScale(0.2f);
            transformableNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 0));
            transformableNode.getScaleController().setEnabled(false);
            transformableNode.setOnTouchListener((hitTestResult, motionEvent1) -> {
                if (motionEvent1.getAction() == MotionEvent.ACTION_UP) {
                    String nodeName = "Nothing";
                    if (hitTestResult.getNode() != null) {
                        return true;
                    }
                    Toast.makeText(AR.this, nodeName + " was touched", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            //transformableNode.getTranslationController().setEnabled(false);
            transformableNode.select();

        });
    }

    private ModelRenderable renderable;

    private void buildModel(File file) {

        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(this, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();

        ModelRenderable
                .builder()
                .setSource(this, renderableSource)
                .setRegistryId(file.getPath())
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(this, "Model built", Toast.LENGTH_SHORT).show();;
                    renderable = modelRenderable;
                });

    }
}
