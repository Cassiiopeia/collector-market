package com.saechan.collectormarket.chat.model.entity;

import com.saechan.collectormarket.global.entity.BaseEntity;
import com.saechan.collectormarket.store.model.entity.Store;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store senderStore;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @Column(length = 500, nullable = false)
  private String message;

  private Boolean readStatus;
}
