package org.stolbov.svyatoslav.System.Dates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class HomeRequest {

    public int sourceNum;
    public int requestNum;
    public double inBufferTime;
    public double inDeviceTime;
    public double endTime;

}
