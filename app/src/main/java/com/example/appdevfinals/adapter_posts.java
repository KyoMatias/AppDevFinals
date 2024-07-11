//package com.example.appdevfinals;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.text.format.DateFormat;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupMenu;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.util.Calendar;
//import java.util.List;
//import java.util.Locale;
//import java.util.Objects;
//
//public class adapter_posts extends RecyclerView.Adapter<com.example.appdevfinals.adapter_posts.MyHolder>{
//
//    Context context;
//    String myUID;
//    private DatabaseReference likeRef, postRef;
//    boolean mProcessLike = false;
//    List<model_post> modelPosts;
//    private MyHolder holder;
//    private int position;
//
//    public adapter_posts(Context context, List<model_post> modelPosts) {
//        this.context = context;
//        this.modelPosts = modelPosts;
//        this.position = position;
//        myUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
//        likeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
//        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");
//    }
//
//    @NonNull
//    @Override
//    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent, false);
//        return new MyHolder(view);
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
//        final String uid = modelPosts.get(position).getUID();
//        String nameh = modelPosts.get(position).getUsername();
//        final String titlee = modelPosts.get(position).getTitle();
//        final String descri = modelPosts.get(position).getDescription();
//        final String ptime = modelPosts.get(position).getPostTime();
//        String dp = modelPosts.get(position).getUserDp();
//        String plike = modelPosts.get(position).getPostLike();
//        final String image = modelPosts.get(position).getUserImage();
//        String email = modelPosts.get(position).getUserEmail();
//        String comm = modelPosts.get(position).getPostComments();
//        final String pid = modelPosts.get(position).getPid();
//        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
////        calendar.setTimeInMillis(Long.parseLong(ptime));
//        String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
//        holder.name.setText(nameh);
//        holder.title.setText(titlee);
//        holder.description.setText(descri);
//        holder.time.setText(timedate);
//        holder.like.setText(plike + " Likes");
//        holder.comments.setText(comm + " Comments");
//        setLikes(holder, ptime);
//        try {
//            Glide.with(context).load(dp).into(holder.picture);
//        } catch (Exception e) {
//
//        }
//        holder.image.setVisibility(View.VISIBLE);
//        try {
//            Glide.with(context).load(image).into(holder.image);
//        } catch (Exception e) {
//
//        }
//        holder.like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), PostLikedByActivity.class);
//                intent.putExtra("pid", pid);
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
//        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int plike = Integer.parseInt(modelPosts.get(position).getPostLike());
//                mProcessLike = true;
//                final String postid = modelPosts.get(position).getPostTime();
//                likeRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (mProcessLike) {
//                            if (dataSnapshot.child(postid).hasChild(myUID)) {
//                                postRef.child(postid).child("plike").setValue("" + (plike - 1));
//                                likeRef.child(postid).child(myUID).removeValue();
//                                mProcessLike = false;
//                            } else {
//                                postRef.child(postid).child("plike").setValue("" + (plike + 1));
//                                likeRef.child(postid).child(myUID).setValue("Liked");
//                                mProcessLike = false;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
//        holder.more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMoreOptions(holder.more, uid, myUID, ptime, image);
//            }
//        });
//        holder.comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, PostDetailsActivity.class);
//                intent.putExtra("pid", ptime);
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    private void showMoreOptions(ImageButton more, String uid, String myuid, final String pid, final String image) {
//        PopupMenu popupMenu = new PopupMenu(context, more, Gravity.END);
//        if (uid.equals(myuid)) {
//            popupMenu.getMenu().add(Menu.NONE, 0, 0, "DELETE");
//        }
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == 0) {
//                    deleteWithImage(pid, image);
//                }
//
//                return false;
//            }
//        });
//        popupMenu.show();
//    }
//
//    private void deleteWithImage(final String pid, String image) {
//        final ProgressDialog pd = new ProgressDialog(context);
//        pd.setMessage("Deleting");
//        StorageReference picref = FirebaseStorage.getInstance().getReferenceFromUrl(image);
//        picref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("ptime").equalTo(pid);
//                query.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            dataSnapshot1.getRef().removeValue();
//                        }
//                        pd.dismiss();
//                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
//
//    private void setLikes(final MyHolder holder, final String pid) {
//        likeRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int currentPosition = holder.getAdapterPosition();
//
//                if (currentPosition == RecyclerView.NO_POSITION) {
//                    return; // The holder is not currently bound
//                }
//
//                if (dataSnapshot.child(pid).hasChild(myUID)) {
//                    holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.accountico, 0, 0, 0);
//                    holder.likeBtn.setText("Liked");
//                } else {
//                    holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.reelsico, 0, 0, 0);
//                    holder.likeBtn.setText("Like");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelPosts.size();
//    }
//
//    class MyHolder extends RecyclerView.ViewHolder {
//        ImageView picture, image;
//        TextView name, time, title, description, like, comments;
//        ImageButton more;
//        Button likeBtn, comment;
//        LinearLayout profile;
//
//        public MyHolder(@NonNull View itemView) {
//            super(itemView);
//            picture = itemView.findViewById(R.id.row_picture_tv);
//            image = itemView.findViewById(R.id.row_image_tv);
//            name = itemView.findViewById(R.id.row_username_tv);
//            time = itemView.findViewById(R.id.row_utime_tv);
//            more = itemView.findViewById(R.id.row_more_button);
//            title = itemView.findViewById(R.id.row_title_tv);
//            description = itemView.findViewById(R.id.row_desc_tv);
//            like = itemView.findViewById(R.id.row_like_tv);
//            comments = itemView.findViewById(R.id.row_comment_tv);
//            likeBtn = itemView.findViewById(R.id.row_like_button);
//            comment = itemView.findViewById(R.id.row_comment_button);
//            profile = itemView.findViewById(R.id.row_post_linear_layout);
//        }
//    }
//
//}
