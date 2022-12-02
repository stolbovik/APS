package org.stolbov.svyatoslav.System.Dates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class HomeRequest {

    private int homeDeviceNum;
    private int requestNum;
    private double generatedTime;

}
