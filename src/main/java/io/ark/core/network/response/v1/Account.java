package io.ark.core.network.response.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ark.core.network.response.NodeResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"address"})
@EqualsAndHashCode(of = {"address"})
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account implements NodeResponse {

    private String address;

    private long unconfirmedBalance;

    private long balance;

    private String publicKey;

    private int unconfirmedSignature;

    private int secondSignature;

    private String secondPublicKey;

}