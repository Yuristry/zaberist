package org.zaber.proxy;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : otter
 */
@Getter
@Setter
public class HitokotoResponse {
    private int id;
    private String uuid;
    private String hitokoto;
    private String type;
    private String from;
    private String from_who;
    private String creator;
    private int creator_uid;
    private int reviewer;
    private String commit_from;
    private String created_at;
    private int length;
}
