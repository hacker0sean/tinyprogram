package com.agent.activity.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="route_scenic")
public class RouteScenic {
    @Id
    private Integer route_id;
    @Id
    private Integer scenic_id;
}
