package com.example.appdevfinals;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class adapter_posts extends RecyclerView.Adapter<com.example.appdevfinals.adapter_posts.MyHolder>{

    Context context;
    String myuid;
    private DatabaseReference liekeref, postref;
    boolean mprocesslike = false;
    private MyHolder holder;
    private int position;
    List<model_post> modelPosts;
    class MyHolder extends RecyclerView.ViewHolder {
        ImageView tvPicture, tvImage;
        TextView tvName, tvTime, tvTitle, tvDescription, tvLike, tvComments;
        ImageButton btnMore;
        Button btnLike, btnComment;
        LinearLayout profile;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvPicture = itemView.findViewById(R.id.row_picture_tv);
            tvImage = itemView.findViewById(R.id.row_image_tv);
            tvName = itemView.findViewById(R.id.row_username_tv);
            tvTime = itemView.findViewById(R.id.row_utime_tv);
            btnMore = itemView.findViewById(R.id.row_more_button);
            tvTitle = itemView.findViewById(R.id.row_title_tv);
            tvDescription = itemView.findViewById(R.id.row_desc_tv);
            tvLike = itemView.findViewById(R.id.row_like_tv);
            tvComments = itemView.findViewById(R.id.row_comment_tv);
            btnLike = itemView.findViewById(R.id.row_like_button);
            btnComment = itemView.findViewById(R.id.row_comment_button);
            profile = itemView.findViewById(R.id.row_post_linear_layout);
        }
    }

    public adapter_posts(Context context, List<model_post> modelPosts) {
        this.context = context;
        this.modelPosts = modelPosts;
        myuid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        liekeref = FirebaseDatabase.getInstance().getReference().child("Likes");
        postref = FirebaseDatabase.getInstance().getReference().child("Posts");
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
//        final String pid = modelPosts.get(holder.getAdapterPosition()).getPid();
        final String pid = postref.getKey();

        final String uid = modelPosts.get(holder.getAdapterPosition()).getUid();
        final String description = modelPosts.get(holder.getAdapterPosition()).getDescription();
        String pcomments = modelPosts.get(holder.getAdapterPosition()).getPcomments();
        String plike = modelPosts.get(holder.getAdapterPosition()).getPlike();
        String ptime = modelPosts.get(holder.getAdapterPosition()).getPtime();
        final String title = modelPosts.get(holder.getAdapterPosition()).getTitle();
        String udp = modelPosts.get(holder.getAdapterPosition()).getUdp();
        String uemail = modelPosts.get(holder.getAdapterPosition()).getUemail();
        final String uimage = modelPosts.get(holder.getAdapterPosition()).getUimage();
        String uname = modelPosts.get(holder.getAdapterPosition()).getUname();

//        When creating a new post, ptime is not null, but when liking posts it becomes null.
//        ptime = "0";

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(ptime));
        String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        Log.d(TAG, "Null Check");
        Log.d(TAG, "ptime: " + ptime);
        Log.d(TAG, "pid: " + pid);

        holder.tvName.setText(uname);
        holder.tvTitle.setText(title);
        holder.tvDescription.setText(description);
        holder.tvTime.setText(timedate);
        holder.tvLike.setText(plike + " Likes");
        holder.tvComments.setText(pcomments + " Comments");
        setLikes(holder, pid);

        try {
            Glide.with(context).load(udp).into(holder.tvPicture);
        } catch (Exception e) {

        }
        holder.tvImage.setVisibility(View.VISIBLE);
        try {
            Glide.with(context).load(uimage).into(holder.tvImage);
        } catch (Exception e) {

        }

        holder.tvLike.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), PostLikedByActivity.class);
            intent.putExtra("pid", pid);
            holder.itemView.getContext().startActivity(intent);
        });
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int plike = Integer.parseInt(modelPosts.get(holder.getAdapterPosition()).getPlike());
                mprocesslike = true;
//                final String pid = modelPosts.get(holder.getAdapterPosition()).getPid();
                final String pid = postref.getKey();
                liekeref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (mprocesslike) {
                            if (dataSnapshot.child(pid).hasChild(myuid)) {
                                postref.child(pid).child("plike").setValue("" + (plike - 1));
                                liekeref.child(pid).child(myuid).removeValue();
                                mprocesslike = false;
                            } else {
                                postref.child(pid).child("plike").setValue("" + (plike + 1));
                                liekeref.child(pid).child(myuid).setValue("Liked");
                                mprocesslike = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreOptions(holder.btnMore, uid, myuid, pid, uimage);
            }
        });
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("pid", pid);
                context.startActivity(intent);
            }
        });
    }

    private void showMoreOptions(ImageButton more, String uid, String myuid, final String pid, final String image) {
        PopupMenu popupMenu = new PopupMenu(context, more, Gravity.END);
        if (uid.equals(myuid)) {
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "DELETE");
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 0) {
                deleteWithImage(pid, image);
            }

            return false;
        });
        popupMenu.show();
    }

    private void deleteWithImage(final String pid, String image) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Deleting");
        StorageReference picReference = FirebaseStorage.getInstance().getReferenceFromUrl(image);
        picReference.delete().addOnSuccessListener(aVoid -> {
            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("pid").equalTo(pid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        dataSnapshot1.getRef().removeValue();
                    }
                    pd.dismiss();
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setLikes(final MyHolder holder, final String pid) {
        postref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int currentPosition = holder.getAdapterPosition();

                if (currentPosition == RecyclerView.NO_POSITION) {
                    return; // The holder is not currently bound
                }

                if (dataSnapshot.child(pid).hasChild(myuid)) {
                    holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_liked, 0, 0, 0);
                    holder.btnLike.setText("Liked");
                } else {
                    holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
                    holder.btnLike.setText("Like");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelPosts.size();
    }



}
