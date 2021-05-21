#!/usr/bin/env perl

foreach my $i (1..3) {
  my $key = "kafka-$i";
  print $key . " => ";
  print `docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $key`;
}
