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

    public long sourceNum;
    public long requestNum;
    public double inBufferTime;
    public double unBufferTime;
    public double endTime;

}
